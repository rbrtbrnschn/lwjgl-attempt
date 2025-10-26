package com.rbrtbrnschn.voxel2

import org.joml.Vector3f

class Color(r: Double, g: Double, b: Double) {
  def toVec3f: Vector3f = new Vector3f(r.toFloat, g.toFloat, b.toFloat)
}
object Color {
  val white: Color = new Color(1.0,1.0,1.0)
  val black: Color = new Color(0.0,0.0,0.0)
}