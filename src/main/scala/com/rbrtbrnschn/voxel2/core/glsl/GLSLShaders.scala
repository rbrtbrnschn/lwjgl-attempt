package com.rbrtbrnschn.voxel2.core.glsl

object GLSLShaders {
  val vertexShaderSrc: String =
    """#version 330 core
      |layout(location=0) in vec3 aPos;
      |layout(location=1) in vec3 aColor;
      |out vec3 vColor;
      |uniform mat4 uMVP;
      |void main() {
      |  gl_Position = uMVP * vec4(aPos, 1.0);
      |  vColor = aColor;
      |}
      |""".stripMargin

  val fragmentShaderSrc: String =
    """#version 330 core
      |in vec3 vColor;
      |out vec4 FragColor;
      |void main() {
      |  FragColor = vec4(vColor, 1.0);
      |}
      |""".stripMargin
}
