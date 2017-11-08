import org.frice.Game
import org.frice.anim.move.AccelerateMove
import org.frice.anim.move.SimpleMove
import org.frice.launch
import org.frice.obj.FObject
import org.frice.obj.SideEffect
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FOval
import org.frice.utils.shape.FRectangle
import org.frice.utils.message.FDialog
import org.frice.utils.time.FTimer
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.function.Consumer

/**
 * Created by ice1000 on 2016/9/11.
 *
 * @author ice1000
 */
class Demo23 : Game() {

	lateinit private var player: FObject
	lateinit private var ground: FObject
	private var direction = KeyEvent.VK_DOWN
	private var jumping = false
	private val timer = FTimer(1000)

	override fun onInit() {
		ground = ShapeObject(ColorResource.IntelliJ_IDEA黑, FRectangle(1000, 10), -10.0, height - 80.0)
		player = ShapeObject(ColorResource.西木野真姬, FOval(10.0, 20.0), 100.0, 100.0)
		player.addAnim(AccelerateMove.getGravity())
		player.targets += ground to SideEffect {
			player.anims.removeAll { a -> a is AccelerateMove || (a is SimpleMove && a.y != 0) }
			jumping = false
		}
		addKeyListener(object : KeyListener {
			override fun keyTyped(e: KeyEvent?) = Unit
			override fun keyPressed(e: KeyEvent?) {
				direction = e?.keyCode ?: KeyEvent.VK_DOWN
				if (!jumping && e?.keyCode ?: KeyEvent.VK_DOWN == KeyEvent.VK_UP) {
					jumping = true
					player.move(0.0, -20.0)
					player.addAnim(AccelerateMove.getGravity())
					player.addAnim(SimpleMove(0, -200))
				}
			}

			override fun keyReleased(e: KeyEvent?) {
				direction = KeyEvent.VK_DOWN
			}
		})
		addObject(player, ground)
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
				targets += player to SideEffect {
					stopped = true
					FDialog(this@Demo23).show("GG!")
					System.exit(0)
				}
			})
		}
	}
}

fun main(args: Array<String>) {
	launch(Demo23::class.java)
}