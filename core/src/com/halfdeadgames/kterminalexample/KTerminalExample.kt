package com.halfdeadgames.kterminalexample

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.halfdeadgames.kassciiterminal.KTerminal
import ktx.app.clearScreen
import ktx.app.use

class KTerminalExample : ApplicationAdapter() {
    companion object {
        val TITLE: String = "KTerminal Example"
        val VERSION: Float = 1f
        val V_WIDTH: Int = 800
        val V_HEIGHT: Int = 480
    }

    lateinit var kTerminal: KTerminal
    lateinit var batch: SpriteBatch

    var timer: Float = 0f
    lateinit var position: Vector2
    lateinit var clearPosition: Vector2

    override fun create() {
        batch = SpriteBatch()

        kTerminal = KTerminal(
                width = 33,
                height = 20,
                tilesetFile = "fontSheet.png",
                scale = 1f,
                defaultBackgroundColor = Color.BLUE,
                defaultForegroundColor = Color.RED,
                inputBatch = batch)

        position = Vector2(0f, 0f)
        clearPosition = Vector2(kTerminal.width - 10f, kTerminal.height - 1f)
    }

    override fun render() {
        clearScreen(0f, 0f, 0f, 1f)

        demo(Gdx.graphics.deltaTime, 0.25f)



        batch.use {
            it.draw(kTerminal.texture, (V_WIDTH - kTerminal.texture.width)/2f, (V_HEIGHT - kTerminal.texture.height)/2f)
        }
    }

    private fun demo(delta: Float, tickTime: Float) {
        timer += delta
        if(timer > tickTime) {
            timer = 0f

            kTerminal.write(position.x.toInt(), position.y.toInt(), randomChar(), randomColor(), randomColor())

            position.x++
            if(position.x > kTerminal.width - 1){
                position.x = 0f
                position.y++
                if(position.y > kTerminal.height - 1) {
                    position.y = 0f
                }
            }

            kTerminal.clear(clearPosition.x.toInt(), clearPosition.y.toInt())

            clearPosition.x++
            if(clearPosition.x > kTerminal.width - 1) {
                clearPosition.x = 0f
                clearPosition.y++
                if(clearPosition.y > kTerminal.height - 1) {
                    clearPosition.y = 0f
                }
            }
        }

    }

    private fun randomChar(): Char {
        return MathUtils.random(254).toChar()
    }

    private fun randomColor(): Color {
        return Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), MathUtils.random(0.75f, 1f))
    }

    override fun dispose() {
        batch.dispose()
        kTerminal.dispose()
    }
}
