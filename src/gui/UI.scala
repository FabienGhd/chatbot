package gui

import scala.swing._
import java.awt
import scala.swing.event._
import scala.swing.MainFrame
import scala.io.StdIn._
import scala.swing.BorderPanel.Position._
import client._
import analyse._
import machine._
import gui._
import javax.swing.ImageIcon

object UI extends MainFrame {

  title = "--- Kazama Chat ---"
  minimumSize = new Dimension(550, 250)
  preferredSize = new Dimension(640, 500) //starting size of the window
  centerOnScreen()
  iconImage = (new ImageIcon("images/kazamaLogo.png")).getImage


  val conversation = new BoxPanel(Orientation.Vertical) //all messages are stored in conversation
  val scrollablePanel = new ScrollPane(conversation)
  val request = new TextField {
    minimumSize = new Dimension(550, 250)
    maximumSize = new Dimension(400, 350)
    tooltip = "Tapez votre requête ici"
    background = awt.Color.decode("#AAB7B8") //input bar is gray
  }
  val setHorizontal = Orientation.Horizontal
  val setVertical = Orientation.Vertical

  //user icon
  def userIcon(): ImageIcon = {
    var userIcon = new ImageIcon("images/userIconBlue.png")
    val image = userIcon.getImage()
    val iconResized = image.getScaledInstance(40, 35, java.awt.Image.SCALE_SMOOTH) //scales it as smooth as possible
    userIcon = new ImageIcon(iconResized)
    userIcon
  }
  
  //robot icons
  def robotIcon(understandable: Boolean): ImageIcon = {
    //when the robot doesn't understand the request
    if(understandable == false) {
      var robotIconCrying = new ImageIcon("images/robotIconCrying.png")
      val imageRobotCry = robotIconCrying.getImage()
      val iconRobotCryResized = imageRobotCry.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH) //scales it as smooth as possible
      robotIconCrying = new ImageIcon(iconRobotCryResized)
      robotIconCrying
      
    } else {
      
      var robotIconSmiling = new ImageIcon("images/robotIconSmiling.png")
      val imageRobotSmile = robotIconSmiling.getImage()
      val iconRobotSmileResized = imageRobotSmile.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH) //scales it as smooth as possible
      robotIconSmiling = new ImageIcon(iconRobotSmileResized)
      robotIconSmiling
    }
  }
  
  def setAutoScroll(): Unit = {
    val bar = scrollablePanel.verticalScrollBar
    val valueAuto = bar.peer.getMaximum
    Swing.onEDT(bar.peer.setValue(valueAuto)) //peers are native graphical user interface widgets
  }
  
  
  //main function
  def process(): Unit = {

    if (!request.text.isEmpty()) {

      val input = ": " + request.text
      val output = MachineImpl.ask(request.text)
      val messageConversation = new Label {
        text = input
      }
 
      //USER boxes
      conversation.contents += new BoxPanel(setHorizontal) {
        
        contents += new BoxPanel(setHorizontal) {
          border = Swing.EmptyBorder(5, 5, 5, 5) //paddings
          contents += Swing.HGlue //left side of the window
          
          contents += new Label {
            icon = userIcon() //adds the icon
          } 
          
          contents += new BoxPanel(setHorizontal) {
            background = awt.Color.decode("#5DADE2") //robot boxes are blue
            contents += messageConversation
            border = Swing.EmptyBorder(9, 5, 9, 5)        
           }  
         } 
       }

      //ROBOT boxes
      conversation.contents += new BoxPanel(setHorizontal) {
        contents += new BoxPanel(setHorizontal) {
          border = Swing.EmptyBorder(5, 5, 5, 5) //paddings
          
          
          for (i <- output.indices) {
          
            border = Swing.EmptyBorder(7, 40, 7, 40) //gaps between messages

            contents += new BoxPanel(setHorizontal) {
              
              if (output(i) == "Je ne comprends pas votre demande") {
                contents += new Label {
                  icon = robotIcon(false)
                } 
                background = awt.Color.decode("#E74C3C") //robot boxes are red when the robot doesn't understand the request
                
                val buttonVoice = new Button("Voix") {
                  background = awt.Color.decode("#E74C3C")
                  tooltip = "Cliquez pour entendre la réponse"
                }
                contents += buttonVoice
                listenTo(request.keys)
                buttonVoice.reactions += {
                case ButtonClicked(_) => val audio = new TextToSpeech(true); audio.init(); audio.say(output)}
                
                contents += new Label(": " + output(i))
                border = Swing.EmptyBorder(5, 5, 5, 5)
                contents += Swing.HGlue //right side of the window

              } else {
                background = awt.Color.decode("#94F834") //robot boxes are green otherwise
                contents += new Label {
                  icon = robotIcon(true)
                } 
         
                val buttonVoice = new Button("Voix") {
                  background = awt.Color.decode("#94F834")
                  tooltip = "Cliquez pour entendre la réponse"
                }
                
                contents += buttonVoice
                listenTo(request.keys)
                buttonVoice.reactions += {
                  case ButtonClicked(_) => val audio = new TextToSpeech(true); audio.init(); audio.say(output)}
                buttonVoice.reactions += {
                  case KeyPressed(_, Key.Alt, _, _) => val audio = new TextToSpeech(true); audio.init(); audio.say(output)}
                
                contents += new Label(":" + output(i))
                border = Swing.EmptyBorder(4, 4, 4, 4)
                contents += Swing.HGlue //right side of the window
              }
            }
          }
        }
      }
      request.text = "" //resets the input bar as soon as we send the request
      conversation.repaint() //keeps track of what has been typed
      conversation.revalidate()
      setAutoScroll()
    }
  }
  

  //results
  contents = new BoxPanel(setVertical) {
    contents += scrollablePanel
    border = Swing.EmptyBorder(10, 10, 10, 10) //creates a specific window for the conversation
    background = awt.Color.decode("#9FE2BF")

    contents += new BoxPanel(setHorizontal) {
      border = Swing.EmptyBorder(8, 15, 8, 8) //centers the input bar 
      contents += request
      
      val buttonSend = new Button("Send↥") {
        tooltip = "Cliquez pour envoyer la requête au robot"
        foreground = awt.Color.decode("#008000")
      }
      
      contents += buttonSend
      buttonSend.borderPainted_=(true)
      background = awt.Color.decode("#9FE2BF")
      
      //sends the request if we click on the button or hit 'enter' with the keyboard
      listenTo(request.keys)
      buttonSend.reactions += {
        case ButtonClicked(_) => process()
      }
      reactions += {
        case KeyPressed(_, Key.Enter, _, _) => process()
      }
    }
  }
  

}
