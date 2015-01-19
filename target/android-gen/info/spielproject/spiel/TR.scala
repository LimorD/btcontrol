package info.spielproject.spiel

import scala.language.implicitConversions
import android.app.{Activity,Dialog}
import android.view.{View,ViewGroup,LayoutInflater}

case class TypedResource[A](id: Int)
case class TypedLayout[A](id: Int)

object TR {


  object layout {

  }
}

trait TypedViewHolder {
  def findViewById(id: Int): View
  def findView[A](tr: TypedResource[A]): A =
    findViewById(tr.id).asInstanceOf[A]
}

trait TypedView extends View with TypedViewHolder
trait TypedActivityHolder extends TypedViewHolder
trait TypedActivity extends Activity with TypedActivityHolder
trait TypedDialog extends Dialog with TypedViewHolder

class TypedLayoutInflater(l: LayoutInflater) {
  def inflate[A](tl: TypedLayout[A], c: ViewGroup, b: Boolean) =
    l.inflate(tl.id, c, b).asInstanceOf[A]
  def inflate[A](tl: TypedLayout[A], c: ViewGroup) =
    l.inflate(tl.id, c).asInstanceOf[A]
}

object TypedResource {
  implicit def viewToTyped(v: View) = new TypedViewHolder {
    def findViewById(id: Int) = v.findViewById(id)
  }
  implicit def activityToTyped(a: Activity) = new TypedViewHolder {
    def findViewById(id: Int) = a.findViewById(id)
  }
  implicit def dialogToTyped(d: Dialog) = new TypedViewHolder {
    def findViewById(id: Int) = d.findViewById(id)
  }
  implicit def layoutInflaterToTyped(l: LayoutInflater) =
    new TypedLayoutInflater(l)
}
