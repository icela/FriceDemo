import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.obj.sub.ShapeObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.graphics.shape.FCircle;
import org.frice.game.utils.time.FTimer;

/**
 * An awesome demo
 *
 * @author SuperSodaSea
 */
public class Demo12 extends Game {

    private ColorResource[] colors;
    private FTimer timer2;

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
        setBack(new ColorResource(0x2b2b2b));
    }

    @Override
    protected void onRefresh() {
        if (timer2.ended()) {
            addObject(new ShapeObject(colors[(int) ((System.currentTimeMillis() / 100) % colors.length)],
                    new FCircle(10), getMousePosition().getX(), getMousePosition().getY()) {{
                getAnims().add(AccelerateMove.getGravity(20));
                getAnims().add(new SimpleMove((int) (getRandom().nextInt(Demo.this.getWidth()) - getX()), -10));
            }});
        }
    }

    public static void main(String[] args) {
        new Demo12();
    }
}
