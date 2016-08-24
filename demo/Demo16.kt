/**
 * Created by ifdog on 2016/8/22.
 */
import org.frice.game.*;
import org.frice.game.anim.FAnim
import org.frice.game.anim.move.SimpleMove
import org.frice.game.event.OnClickEvent
import org.frice.game.obj.collide.OnCollideEvent
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.graphics.shape.FCircle
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.message.log.FLog
import java.awt.FileDialog
import java.awt.MouseInfo
import java.util.*

fun main(args: Array<String>) {
    untitledDemo()
}

class  untitledDemo:Game(){
    private lateinit var 拍:ShapeObject
    private lateinit var 球:ShapeObject
    private lateinit var 左墙:ShapeObject
    private lateinit var 右墙:ShapeObject
    private lateinit var 上墙:ShapeObject
    private  var 发球 = false
    private var xa = -200
    private var ya = -200

    public override  fun onInit() {
        setBounds(100,100,800,600)
        拍 = ShapeObject(ColorResource.BLACK,FRectangle(100,15),100.0,500.0)
        球 = ShapeObject(ColorResource.南小鸟,FCircle(5.0),120.0,489.0)
        左墙 = ShapeObject(ColorResource.BLACK,FRectangle(20,700),-20.0,-50.0)
        右墙 = ShapeObject(ColorResource.BLACK,FRectangle(20,700),795.0,-50.0)
        上墙 = ShapeObject(ColorResource.BLACK,FRectangle(900,20),-50.0,-20.0)
        addObject(拍)
        addObject(球)
        addObject(左墙)
        addObject(右墙)
        addObject(上墙)

        for(item:ShapeObject in arrayOf(左墙,右墙,上墙,拍)){
            球.targets.add(Pair(item,object:OnCollideEvent{
                override fun handle() {
                    球.anims.clear()
                    Check(球,item)
                    球.anims.add(SimpleMove(xa,ya))
                }
            }))
        }
    }

    public  override fun onRefresh() {
        val point =MouseInfo.getPointerInfo().location
        var theX = point.x-this.x.toDouble()-5
        if(0<theX&&theX<700){
            拍.x = theX
            if(!发球){
                球.x = 拍.x+30
            }
        }
        if (球.y>600){
            FDialog(this).show("Game over")
            System.exit(0)
        }
    }

    public override  fun onClick(e: OnClickEvent?) {
        发球 = true
        xa = ((random.nextGaussian()-0.5)*200).toInt()
        ya = ((random.nextGaussian()-0.5)*200).toInt()
        球.anims.add(SimpleMove(xa,ya))
    }
    public fun Check(active_obj:ShapeObject,static_obj:ShapeObject){
        val x1 = static_obj.x
        val x2 = static_obj.x+static_obj.width
        val y1 = static_obj.y
        val y2 = static_obj.y+static_obj.height
        val x = active_obj.x
        val y = active_obj.y
        //FLog.d("$x   $y   $x1   $y1   $x2   $y2")
        when{
            (x>x1-5)&&(x<x2+5)&&(y <= y1+5) -> ya = (-1.05*ya).toInt()
            (x>x1-5)&&(x<x2+5)&&(y > y2-5) -> ya = (-1.05*ya).toInt()
            (x <= x1+5)&&(y>y1-5)&&(y<=y2+5) -> xa = (-1.05*xa).toInt()
            (x > x2-5)&&(y>y1-5)&&(y<y2+5) -> xa = (-1.05*xa).toInt()
        }
    }
}