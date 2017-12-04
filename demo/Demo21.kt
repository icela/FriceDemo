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
import org.frice.obj.sub.ImageObject
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.resource.graphics.ParticleResource
import org.frice.resource.image.WebImageResource
import org.frice.utils.QuadTree
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

	private lateinit var colored: ImageObject

	override fun onInit() {
		super.onInit()
		autoGC = true

		colored = ImageObject(WebImageResource("https://avatars1.githubusercontent.com/u/21008243?v=3&s=200"), 300.0, 300.0)
		colored.collisionBox = object : FShapeQuad {
			override val height = 50.0
			override val width = height
			override val x = 350.0
			override val y = 350.0
		}

		addObject(ParticleEffect(ParticleResource(this, width / 10, height / 10, 0.01), width * 0.1, height * 0.1))
		addObject(SimpleButton(text = "I am a button", x = 30.0, y = 30.0, width = 100.0, height = 30.0).also {
			it.onMouseListener = Consumer {
				val obj = ShapeObject(ColorResource.西木野真姬, FOval(40.0, 30.0), 100.0, 100.0).apply {
					mass = 3.0
					addForce(-1.0, -1.0)
					addAnim(SimpleMove(200, 200))
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
		addObject(colored)

		FLog.v(ColorResource.小泉花阳.color.rgb.greyify())
	}

	val random = Random(System.currentTimeMillis())

	override fun onRefresh() {
		super.onRefresh()
		if (timer.ended()) {
			objs.removeAll(PhysicalObject::died)
			addObject(ShapeObject(ColorResource.IntelliJ_IDEA黑, FCircle(10.0), mouse.x, mouse.y).apply {
				addAnim(AccelerateMove.getGravity())
				addAnim(SimpleMove(random.nextInt(400) - 200, 0))
				targets.clear()
				addCollider(colored, SideEffect { res = ColorResource.MAGENTA })
				objs.forEach {
					addCollider(it, SideEffect {
						anims.clear()
						targets.clear()
						addAnim(SimpleMove(0, -300))
						addAnim(SimpleScale(1.1, 1.1))
					})
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