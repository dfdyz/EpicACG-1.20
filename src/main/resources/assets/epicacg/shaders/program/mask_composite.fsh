#version 150

#define _PI_ 3.14159265359

uniform sampler2D DiffuseSampler;
uniform sampler2D Mask;
uniform sampler2D Content;
uniform float InterScale;

in vec2 texCoord;
out vec4 fragColor;

void main(){
    vec4 mask = texture(Mask, texCoord);

    float scaled = InterScale * mask.a;
    vec2 patchedCoord = texCoord / scaled;

    if(mask.a > 0.001){
        patchedCoord += (scaled - 1) * mask.gb;

        fragColor = vec4(texture(DiffuseSampler, patchedCoord).rgb * (1.05 + mask.r * 0.1), 1);
    }
    else{
        fragColor = vec4(texture(DiffuseSampler, texCoord).rgb, 1);
    }

}
