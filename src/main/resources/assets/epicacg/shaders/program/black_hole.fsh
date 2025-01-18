#version 150

#define _PI_ 3.14159265359

uniform sampler2D DiffuseSampler;
uniform sampler2D Mask;
uniform vec2 wh_Len; // width / height, len

in vec2 texCoord;
out vec4 fragColor;

float funcB(float x){
    return (-25* (x-0.5) * (x-0.5) + 0.5);
}


void main(){
    if(wh_Len.x < 0){
        fragColor = vec4(texture(DiffuseSampler, texCoord).rgb, 1);
        return;
    }

    vec4 offset = texture(Mask, texCoord);

    if(offset.a > 0.001){
        vec2 off = -offset.rg + vec2(0.5, 0.5);

        float l = sqrt(off.x * off.x + off.y * off.y);
        if(l > 0.001) {
            if(l<0.4){
                l = 1.5625 * l;
            }
            else{
                l = funcB(l) / l;
            }
        }
        else l = 0.001;
        off *= l;
        off.g = off.g * wh_Len.x;
        vec2 patchedCoord = texCoord + off * wh_Len.y;

        fragColor = vec4(texture(DiffuseSampler, patchedCoord).rgb * 1.05, 1);
    }
    else{
        fragColor = vec4(texture(DiffuseSampler, texCoord).rgb, 1);
    }

}
