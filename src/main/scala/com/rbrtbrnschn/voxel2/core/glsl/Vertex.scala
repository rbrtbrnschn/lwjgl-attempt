package com.rbrtbrnschn.voxel2.core.glsl

import org.joml.Vector3f

case class Vertex(position: Position, color: Color) {
  @deprecated("Use [[Vertex]](position: Position, color: Color) instead", "1.0.0")
  def this(vector3f: Vector3f, color: Color) = this(Position(vector3f.x, vector3f.y, vector3f.z), color)
  @deprecated("Use [[Vertex]](position: Position, color: Color) instead", "1.0.0")
  def this(vector3f: Vector3f, color: Vector3f) = this(Position(vector3f.x, vector3f.y, vector3f.z), color = Color(color.x, color.y, color.z))
}

object Vertex {
  @deprecated("Use [[Vertex]](position: Position, color: Color) instead", "1.0.0")
  def apply(vector3f: Vector3f, color: Color) = new Vertex(Position(vector3f.x, vector3f.y, vector3f.z), color)
  @deprecated("Use [[Vertex]](position: Position, color: Color) instead", "1.0.0")
  def apply(vector3f: Vector3f, color: Vector3f) = new Vertex(Position(vector3f.x, vector3f.y, vector3f.z), color = Color(color.x, color.y, color.z))
}