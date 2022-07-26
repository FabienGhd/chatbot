package gui

import javax.sound.sampled.AudioInputStream
import marytts.LocalMaryInterface
import marytts.MaryInterface
import marytts.util.data.audio.AudioPlayer


class TextToSpeech(active: Boolean) {
   val marytts: MaryInterface = new LocalMaryInterface()
   val ap: AudioPlayer = new AudioPlayer()
 
   def init(): Unit = {
    marytts.setVoice("upmc-pierre-hsmm")
  }
   
   /*
   //TODO
   def getVoice(lang: String): String = {
     if(lang.equals("nomFR")) {"voice-upmc-pierre-hsmm-5.1"} //French male voice
     marytts.setVoice("voice-upmc-pierre-hsmm-5.1")
     ""
   }
   */
   //TODO
   def say(text: List[String]): Unit = {
     if(active == true) {
       var wordsToSay: String = ""
       //marytts.setVoice("voice-upmc-pierre-hsmm-5.1")
       for(i <- text) {
         wordsToSay += i 
       }
       try{
         val audio: AudioInputStream = marytts.generateAudio(wordsToSay)
         ap.setAudio(audio)
         ap.start()
         ap.join()
       }    
       catch {
         case e: Throwable => System.err.println("Error saying phrase. You've swiched to another language.")
       }
     }
   }
}