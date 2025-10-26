package com.rbrtbrnschn.voxel2.core

import com.rbrtbrnschn.voxel2.core.glsl.{Color, Position, Vertex}

case class BlockColors(
                      left: Color,
                      right: Color,
                      up: Color,
                      down: Color,
                      front: Color,
                      back: Color
                      )

object BlockColors {
  val white: BlockColors = BlockColors(left = Color.white, right = Color.white, up = Color.white, down = Color.white, front = Color.white, back = Color.white)
  val green: BlockColors = BlockColors(left = Color.green,right = Color.green,up = Color.green,down = Color.green,front = Color.green,back = Color.green)
}

case class Block(colors: BlockColors) {
  def faces: Array[Array[Vertex]] = {
   Block.facePositions.zipWithIndex.collect {
     case (face, i) => {
       val _colors = i match {
         case 0 => colors.front
         case 1 => colors.back
         case 2 => colors.left
         case 3 =>  colors.right
         case 4 =>  colors.up
         case 5 =>  colors.down
       }
       face.map(pos => Vertex(pos, color = _colors))
     }
   }
  }
}
object Block {
  private val facePositions = Array(
    // front
    Array(
      Position(-0.5f,-0.5f, 0.5f),
      Position( 0.5f,-0.5f, 0.5f),
      Position( 0.5f, 0.5f, 0.5f),
      Position(-0.5f,-0.5f, 0.5f),
      Position( 0.5f, 0.5f, 0.5f),
      Position(-0.5f, 0.5f, 0.5f)
    ),
    // back
    Array(
      Position(-0.5f,-0.5f,-0.5f),
      Position( 0.5f,-0.5f,-0.5f),
      Position( 0.5f, 0.5f,-0.5f),
      Position(-0.5f,-0.5f,-0.5f),
      Position( 0.5f, 0.5f,-0.5f),
      Position(-0.5f, 0.5f,-0.5f)
    ),
    // left
    Array(
      Position(-0.5f,-0.5f,-0.5f),
      Position(-0.5f,-0.5f, 0.5f),
      Position(-0.5f, 0.5f, 0.5f),
      Position(-0.5f,-0.5f,-0.5f),
      Position(-0.5f, 0.5f, 0.5f),
      Position(-0.5f, 0.5f,-0.5f)
    ),
    // right
    Array(
      Position(0.5f,-0.5f,-0.5f),
      Position(0.5f,-0.5f, 0.5f),
      Position(0.5f, 0.5f, 0.5f),
      Position(0.5f,-0.5f,-0.5f),
      Position(0.5f, 0.5f, 0.5f),
      Position(0.5f, 0.5f,-0.5f)
    ),
    // top
    Array(
      Position(-0.5f,0.5f,-0.5f),
      Position(-0.5f,0.5f, 0.5f),
      Position( 0.5f,0.5f, 0.5f),
      Position(-0.5f,0.5f,-0.5f),
      Position( 0.5f,0.5f, 0.5f),
      Position( 0.5f,0.5f,-0.5f)
    ),
    // bottom
    Array(
      Position(-0.5f,-0.5f,-0.5f),
      Position(-0.5f,-0.5f, 0.5f),
      Position( 0.5f,-0.5f, 0.5f),
      Position(-0.5f,-0.5f,-0.5f),
      Position( 0.5f,-0.5f, 0.5f),
      Position( 0.5f,-0.5f,-0.5f)
    )
  )
  val grass: Block = Block(colors = BlockColors.green)
  val cloud: Block = Block(colors = BlockColors.white)
}