package se.fransman.tg.shared

expect class DebugLogger (tagString : String) {
  val tag : String
  fun log(message: String)
}