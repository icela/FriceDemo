package org.frice.game.utils.data

import org.frice.game.Game
import org.frice.game.anim.move.AccelerateMove
import org.frice.game.anim.move.SimpleMove
import org.frice.game.obj.FObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.time.FTimer
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

/**
 * Created by ice1000 on 2016/9/11.
 *
 * @author ice1000
 */
class Demo23() : Game() {

	lateinit private var player: FObject
	lateinit private var ground: FObject
	private var direction = KeyEvent.VK_DOWN
	private var jumping = false
	private val timer = FTimer(1000)

	override fun onInit() {
		ground = ShapeObject(ColorResource.IntelliJ_IDEA黑, FRectangle(1000, 10), -10.0, height - 80.0)
		player = ShapeObject(ColorResource.西木野真姬, FOval(10.0, 20.0), 100.0, 100.0)
		player.anims.add(AccelerateMove.getGravity())
		player.targets.add(Pair(ground, FObject.OnCollideEvent.from {
			player.anims.removeAll { a -> a is AccelerateMove || (a is SimpleMove && a.y != 0) }
			jumping = false
		}))
		addKeyListener(object : KeyListener {
			override fun keyTyped(e: KeyEvent?) = Unit
			override fun keyPressed(e: KeyEvent?) {
				direction = e?.keyCode ?: KeyEvent.VK_DOWN
				if (!jumping && e?.keyCode ?: KeyEvent.VK_DOWN == KeyEvent.VK_UP) {
					jumping = true
					player.move(0.0, -20.0)
					player.anims.add(AccelerateMove.getGravity())
					player.anims.add(SimpleMove(0, -200))
				}
			}

			override fun keyReleased(e: KeyEvent?) {
				direction = KeyEvent.VK_DOWN
			}
		})
		addObject(player)
		addObject(ground)
	}

	override fun onRefresh() {
		when (direction) {
			KeyEvent.VK_LEFT -> player.move(-0.0005, 0.0)
			KeyEvent.VK_RIGHT -> player.move(0.0005, 0.0)
		}
		if (player.x < 10) player.x = 10.0
		if (player.x > width - 30) player.x = width - 30.0
		if (timer.ended()) {
			addObject(ShapeObject(ColorResource.BLUE, FRectangle(30, 30), width + 100.0, ground.y - 30).apply {
				anims.add(SimpleMove(-1000, 0))
				targets.add(Pair(player, FObject.OnCollideEvent.from {
					stopped = true
					FDialog(this@Demo23).show("GG!")
					System.exit(0)
				}))
			})
		}
	}
}

fun main(args: Array<String>) {
	Demo23()
}