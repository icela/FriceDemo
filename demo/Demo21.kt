import org.frice.Game
import org.frice.anim.RotateAnim
import org.frice.anim.move.AccelerateMove
import org.frice.anim.move.SimpleMove
import org.frice.anim.scale.SimpleScale
import org.frice.launch
import org.frice.obj.PhysicalObject
import org.frice.obj.SideEffect
import org.frice.obj.button.SimpleButton
import org.frice.obj.effects.ParticleEffect
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.resource.graphics.ParticleResource
import org.frice.utils.data.Preference
import org.frice.utils.data.XMLPreference
import org.frice.utils.greyify
import org.frice.utils.message.FLog
import org.frice.utils.shape.*
import org.frice.utils.time.FTimer
import java.util.*
import java.util.function.Consumer

/**
 * Sample
 * Created by ice1000 on 2016/8/21.
 *
 * @author ice1000
 */
class Demo21 : Game() {
	private lateinit var preference: Preference
	private lateinit var xmlPreference: XMLPreference
	private val timer = FTimer(200)
	private val objs = LinkedList<PhysicalObject>()

	override fun onInit() {
		super.onInit()
		autoGC = true

		addObject(ParticleEffect(ParticleResource(this, width / 10, height / 10, 0.01), width * 0.1, height * 0.1))
		addObject(SimpleButton(text = "I am a button", x = 30.0, y = 30.0, width = 100.0, height = 30.0).apply {
			onMouseListener = Consumer {
				val obj = ShapeObject(ColorResource.西木野真姬, FOval(40.0, 30.0), 100.0, 100.0).apply {
					mass = 3.0
					addForce(-1.0, -1.0)
					addAnim(SimpleMove(400, 400))
					addAnim(SimpleScale(1.1, 1.0))
					addAnim(RotateAnim(0.1))
				}
				objs.add(obj)
				addObject(obj)
			}
		})
		//		AudioManager.getPlayer("1.wav").start()
		//		AudioManager.play("1.wav")

		//		setCursor(WebImageResource("https://avatars1.githubusercontent.com/u/16477304?v=3&s=84"))

		preference = Preference("settings.properties")
		preference.insert("fuck", "microsoft")

		xmlPreference = XMLPreference("settings.xml")
		xmlPreference.insert("shit", "goddamn it")

		FLog.v(preference.query("fuck", "Apple"))
		FLog.v(xmlPreference.query("shit", "no we don't"))

		FOval(1.0, 1.0)
		FCircle(1.0)
		FPoint(1, 2)

		//		addObject(ImageObject(FileImageResource("1.png"), 10.0, 10.0))
		//		addObject(ImageObject(WebImageResource("https://avatars1.githubusercontent.com/u/21008243?v=3&s=200"),
		//				10.0, 10.0))

		FLog.v(ColorResource.小泉花阳.color.rgb.greyify())
	}

	val random = Random(System.currentTimeMillis())

	override fun onRefresh() {
		super.onRefresh()
		if (timer.ended()) {
			objs.removeAll { o -> o.died }
			addObject(ShapeObject(ColorResource.IntelliJ_IDEA黑, FCircle(10.0), mouse.x, mouse.y).apply {
				anims.add(AccelerateMove.getGravity())
				anims.add(SimpleMove(random.nextInt(400) - 200, 0))
				targets.clear()
				objs.forEach { o ->
					targets += o to SideEffect {
						anims.clear()
						targets.clear()
						addAnim(SimpleMove(0, -300))
						addAnim(SimpleScale(1.1, 1.1))
						res = ColorResource.MAGENTA
					}
				}
			})
		}
	}

//	override fun onMouse(e: OnMouseEvent) {
//		super.onMouse(e)
//		FLog.v(e.toString())
//		FLog.v(mousePosition)
//	}

	override fun onExit() = System.exit(0)
}

fun main(args: Array<String>) {
	launch(Demo21::class.java)
}