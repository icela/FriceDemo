import kotlin.Pair;
import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.obj.FObject;
import org.frice.game.obj.sub.ShapeObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.graphics.shape.FCircle;
import org.frice.game.utils.graphics.shape.FRectangle;
import org.frice.game.utils.time.FTimer;

/**
 * An awesome demo
 *
 * @author SuperSodaSea
 */
public class Demo13 extends Game {

    private ColorResource[] colors;
    private FTimer timer2;
    private FObject object;

    @Override
    public void onInit() {
        timer2 = new FTimer(30);
        colors = new ColorResource[]{
                ColorResource.东条希,
                ColorResource.高坂穗乃果,
                ColorResource.西木野真姬,
                ColorResource.矢泽妮可,
                ColorResource.洵濑绘理,
                ColorResource.星空凛,
                ColorResource.南小鸟
        };
        object = new ShapeObject(new ColorResource(0xffffff), new FRectangle(getWidth(), 10), 1, getHeight() - 50);
        addObject(object);
        setBack(new ColorResource(0x2b2b2b));
    }

    @Override
    protected void onRefresh() {
        if (timer2.ended()) {
            addObject(new ShapeObject(colors[(int) ((System.currentTimeMillis() / 100) % colors.length)],
                    new FCircle(10), getMousePosition().getX(), getMousePosition().getY()) {{
                getAnims().add(new SimpleMove((int) (getRandom().nextInt(Demo13.this.getWidth()) - getX()), -500));
                getAnims().add(AccelerateMove.getGravity(20));
                getTargets().add(new Pair<>(object, () -> {
                    getAnims().remove(1);
                    getAnims().add(AccelerateMove.getGravity(20));
                }));
            }});
        }
    }

    public static void main(String[] args) {
        new Demo13();
    }
}
