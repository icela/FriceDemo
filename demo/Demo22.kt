package org.frice.game

import org.frice.Game
import org.frice.launch
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FCircle
import org.frice.utils.shape.FRectangle
import org.frice.utils.time.FTimer
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class Demo22 : Game() {

	private var direction = KeyEvent.VK_LEFT
	private var xx = 30
	private var yy = 20
	private var fx = 10
	private var fy = 10
	private var timer = FTimer(100)
	private var isIncreasing = true
	lateinit private var body: Queue<ShapeObject>
	lateinit private var food: ShapeObject

	override fun onInit() {
		super.onInit()
		setLocationRelativeTo(null)
		setSize(600, 400)
		title = "Snake game demo"
		addKeyListener(object : KeyListener {
			override fun keyTyped(e: KeyEvent) = Unit
			override fun keyPressed(e: KeyEvent) {
				direction = e.keyCode
			}

			override fun keyReleased(e: KeyEvent) = Unit
		})
		body = LinkedBlockingQueue<ShapeObject>()
		food = generateFood(10, 10)
		addObject(food)
		addObject(generateBody(30, 20).apply { body.add(this) })
		autoGC = false
		isResizable = false
	}

	override fun onRefresh() {
		super.onRefresh()
		if (timer.ended()) {
			val obj: ShapeObject = when (direction) {
				KeyEvent.VK_LEFT -> generateBody(--xx, yy)
				KeyEvent.VK_RIGHT -> generateBody(++xx, yy)
				KeyEvent.VK_UP -> generateBody(xx, --yy)
				else -> generateBody(xx, ++yy)
			}
			xx = moved(xx, true)
			yy = moved(yy, false)
			if (xx == fx && yy == fy) {
				removeObject(food)
				isIncreasing = true
				fx = random.nextInt(this.width / 10 - 1)
				fy = random.nextInt(this.height / 10 - 1)
				food = generateFood(fx, fy)
				addObject(food)
			}
			body.add(obj)
			addObject(obj)
			if (!isIncreasing) removeObject(body.poll())
			isIncreasing = false
		}
	}

	private fun generateBody(x: Int, y: Int) = ShapeObject(ColorResource.吾王蓝, FRectangle(10, 10), (x * 10).toDouble(), (y * 10).toDouble())
	private fun generateFood(x: Int, y: Int) = ShapeObject(ColorResource.如果奇迹有颜色那么一定是橙色, FCircle(5.0), (x * 10).toDouble(), (y * 10).toDouble())
	private fun moved(i: Int, isX: Boolean) = if (isX) (i + this.width / 10 - 2) % (this.width / 10 - 2) else (i + (this.height / 10 - 4)) % (this.height / 10 - 4)

	companion object {
		@JvmStatic fun main(args: Array<String>) = launch(Demo22::class.java)
	}
}
