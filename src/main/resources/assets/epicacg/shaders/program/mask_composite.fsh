#version 150

#define _PI_ 3.14159265359

uniform sampler2D DiffuseSampler;
uniform sampler2D Mask;
uniform sampler2D Content;


in vec2 texCoord;
out vec4 fragColor;

void main(){
    vec4 mask = texture(Mask, texCoord);
    if (mask.a > 0.001f){
        vec3 cont = texture(Content, texCoord).rgb;
        vec3 org = texture(DiffuseSampler, texCoord).rgb;
        fragColor = vec4(org * (1 - mask.a) + cont * mask.rgb * mask.a, 1);
    }
    else{
        fragColor = vec4(texture(DiffuseSampler, texCoord).rgb, 1);
    }
}
