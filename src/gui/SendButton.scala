package gui

import scala.swing._
import event._
import java.awt.Color

class SendButton(txt: String, from: TextField) extends Button {
  text = txt
  tooltip = "Envoyez la requête au chatBot"
  from.columns = 30
}