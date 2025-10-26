package com.rbrtbrnschn.voxel2.core

import org.joml.Vector3f

case class Position(x: Double, y: Double, z: Double) {
  def toVector3f(): Vector3f = new Vector3f(x.toFloat, y.toFloat, z.toFloat)
}
