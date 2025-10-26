package com.rbrtbrnschn.voxel2.core

import com.rbrtbrnschn.voxel2.core.glsl.{Mesh, Position}
import org.joml.Vector3f

object ChunkGenerator {
  private var generatedCoordinates = List.empty[Position]

  def generate(block: Block): List[Mesh] = {
    val nextPositionOffset = getNextFreeCoordinates

    val offsetX = (nextPositionOffset.x * 16).toInt
    val offsetY = (nextPositionOffset.y * 16).toInt
    val offsetZ = (nextPositionOffset.z * 16).toInt
    val cubes = (offsetX to offsetX + 15).flatMap(x => {
      (offsetY to offsetY + 15).flatMap(y => {
        (offsetZ  to offsetZ + 15).map(z => {
          createCube(block,x,y,z)
        })
      })
    })
    generatedCoordinates = generatedCoordinates :+ nextPositionOffset
    cubes.toList

  }

  // impl for testing purposes only
  private def getNextFreeCoordinates: Position = {
    Position(generatedCoordinates.lastOption.map(_.x + 1).getOrElse(0.0f), 0.0f, 0.0f)
  }

  private def createCube(block: Block, x: Int, y: Int, z: Int): Mesh = {
    val verts = block.faces.flatten.map { vertex =>
      val newPos = new Vector3f(vertex.position.toVector3f).add(x, y, z)
      vertex.copy(position = Position(newPos.x, newPos.y, newPos.z))
    }
    new Mesh(verts)
  }
}
