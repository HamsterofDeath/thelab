package hod.trainingnew.rain

import hod.trainingold.training.EmptyFrame

import java.awt.{Color, Graphics}

object RainDemo {
  def main(args: Array[String]): Unit = {
    val rs = new RainSimulator()
    rs.start()
  }

  class RainSimulator extends EmptyFrame {

    override def breite = 2000

    var tropfen = List.empty[RegenTropfen]

    def zufallsTropfen = {
      val start = Position(zufall(breite), 0)
      val bewegung = Bewegung(0.0, zufall(5.0)+0.1)
      RegenTropfen(start, bewegung)
    }

    override def zeichnen(g: Graphics): Unit = {
      super.zeichnen(g)
      tropfen = tropfen ++ (List.fill(15)(zufallsTropfen))

      g.setColor(Color.BLUE)
      tropfen.foreach { tropfen =>
        g.fillRoundRect(
          tropfen.position.woX.toInt,
          tropfen.position.woY.toInt,
          2,
          2,
          2,
          2
        )
      }

      tropfen = tropfen.map(_.next)
      tropfen = tropfen.filter(_.position.woY < hoehe)
    }

  }

  case class Position(woX: Double, woY: Double)
  case class Bewegung(x: Double, y: Double)
  case class RegenTropfen(position: Position, bewegung: Bewegung) {
    def next =
      RegenTropfen(
        Position(position.woX + bewegung.x, position.woY + bewegung.y),
        bewegung
      )

  }

}