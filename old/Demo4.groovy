import org.frice.Game
import org.frice.event.OnMouseEvent
import org.frice.platform.FriceImage
import org.frice.resource.image.FileImageResource
import org.frice.resource.image.ImageResource
import org.frice.utils.data.Preference
import org.frice.utils.message.FDialog
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static org.frice.Initializer.launch

/**
 * Groovy sample
 *
 * Demo for database operating
 */
class Demo4 extends Game {

	def fuck = new Preference("test.xml")
	FDialog dialog
	def closed = false

	def image

	@Override
	void onInit() {
		dialog = new FDialog(this)
		image = new FileImageResource("./test.png")

		/*
		 * Groovy and Kotlin
		 */
		cursor = new ImageResource() {
			@Override
			FriceImage getImage() {
				Demo4.this.image.image
			}

			@Override
			void setImage(@NotNull FriceImage bufferedImage) {
				Demo4.this.image.image = bufferedImage
			}
		}

	}

	@Override
	void onRefresh() {
		if (!closed) switch (dialog.confirm("what do U want?")) {
			case 0:
				fuck.insert(
						dialog.input("inserting, key?"),
						dialog.input("inserting, value?"))
				break
			case 1:
				dialog.confirm(fuck.query(
						dialog.input("querying, key?"),
						dialog.input("querying, value?")).toString())
				break
			case 2:
				closed = true
				break
		}
	}

	@Override
	void onMouse(@Nullable OnMouseEvent e) {
		// Do something
	}

	static void main(String[] args) {
		launch Demo4.class
	}
}