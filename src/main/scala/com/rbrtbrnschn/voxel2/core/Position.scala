package com.rbrtbrnschn.voxel2.core

import org.joml.Vector3f

case class Position(x: Float, y: Float, z: Float) {
  def toVector3f: Vector3f = new Vector3f(x, y, z)
}
