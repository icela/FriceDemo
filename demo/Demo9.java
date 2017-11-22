import kotlin.Pair;
import org.frice.Game;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.effects.ParticleEffect;
import org.frice.obj.sub.ImageObject;
import org.frice.platform.adapter.JvmImage;
import org.frice.resource.graphics.ColorResource;
import org.frice.resource.graphics.ParticleResource;
import org.frice.resource.image.FileImageResource;
import org.frice.utils.audio.AudioManager;
import org.frice.utils.time.FTimer;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.frice.Initializer.launch;

public class Demo9 extends Game {
	private int cnt = 0;

	public static void main(String[] args) {
		launch(Demo9.class);
	}

	private ImageObject boss;
	private FTimer timer = new FTimer(600);

	@Override
	public void onInit() {
		super.onInit();
		boss = new ImageObject(new FileImageResource("3.png"), 10.0, 20.0);
		ImageObject plane = new ImageObject(new FileImageResource("1.png"));
		setBounds(100, 100, 500, 500);
		addObject(new ParticleEffect(new ParticleResource(this, 500, 500,
				ColorResource.WHITE, ColorResource.GREEN), 0, 0));
		addObject(boss);
		addObject(plane);
		addKeyPressedEvent(KeyEvent.VK_SPACE, e -> {
			ImageObject object = new ImageObject(new FileImageResource("2.png"), plane.getX(), plane.getY());
			object.addAnim(new SimpleMove(0, -300));
			object.addCollider(new Pair<>(boss, () -> {
				AudioManager.play("1.wav");
				removeObject(object);
			}));
			addObject(object);
		});
		addKeyPressedEvent(KeyEvent.VK_A, e -> plane.setX(plane.getX() - 20));
		addKeyPressedEvent(KeyEvent.VK_D, e -> plane.setX(plane.getX() + 20));
		addKeyPressedEvent(KeyEvent.VK_S, e -> plane.setY(plane.getY() + 20));
		addKeyPressedEvent(KeyEvent.VK_W, e -> plane.setY(plane.getY() - 20));
		addKeyPressedEvent(KeyEvent.VK_C, e -> new Thread(() -> {
			try {
				ImageIO.write(((JvmImage) getScreenCut().getImage()).getImage(), "png", new File("截屏" + cnt++ + ".png"));
			} catch (IOException ignored) {
				System.out.println("车祸现场");
			}
		}).start());
	}

	Random random = new Random(System.currentTimeMillis());

	@Override
	public void onRefresh() {
		if (timer.ended()) {
			boss.getAnims().clear();
			boss.addAnim(new SimpleMove(
					random.nextInt(500) - (int) boss.getX(),
					random.nextInt(500) - (int) boss.getY()));
		}
	}
}


// 苟利国家生死以，岂因祸福避趋之！
// 祝长者生日快乐！


