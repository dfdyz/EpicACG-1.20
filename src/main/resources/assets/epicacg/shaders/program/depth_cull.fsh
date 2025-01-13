#version 150

uniform sampler2D DiffuseSampler; //src
uniform sampler2D SrcDepth;
uniform sampler2D GlobalDepth;

in vec2 texCoord;
out vec4 fragColor;

void main(){
    vec4 src = texture(DiffuseSampler, texCoord);
    vec4 srcdepth = texture(SrcDepth, texCoord);
    vec4 gdepth = texture(GlobalDepth, texCoord);

    fragColor = srcdepth.r + 0.0001 < gdepth.r ? vec4(0,0,0,0) : src;
    //fragColor = vec4(cut.x > 0.1 ? vec3(1,1,1) : col.rgb,1);
}
