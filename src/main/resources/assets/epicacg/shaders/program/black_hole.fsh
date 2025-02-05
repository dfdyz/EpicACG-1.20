#version 150

#define _PI_ 3.14159265359

uniform sampler2D DiffuseSampler;
uniform sampler2D Mask;
uniform vec2 wh_Len; // width / height, len

in vec2 texCoord;
out vec4 fragColor;

float funcZ(float x){
    if(x < 0){
        return 0;
    }
    else{
        return 100 / 9 * x * x;
    }
}

float funcA(float x){
    return - 100 / 9 * (x-0.3) * (x-0.3) + 0.5;
}

float funcB(float x){
    return -25* (x-0.3) * (x-0.3) + 0.5;
}

float funcC(float x){
    return 25* (x-0.5) * (x-0.5);
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
            if(l < 0.15){
                l = funcZ(l) / l;
            }
            else if(l >= 0.15 && l<0.3){
                l = funcA(l) / l;
            }
            else if(l >= 0.3 && l<0.4){
                l = funcB(l) / l;
            }
            else{
                l = funcC(l) / l;
            }
        }
        else l = 0.0;
        off *= l;
        off.g = off.g * wh_Len.x;
        vec2 patchedCoord = texCoord + off * wh_Len.y * offset.a;

        fragColor = vec4(texture(DiffuseSampler, patchedCoord).rgb, 1);
    }
    else{
        fragColor = vec4(texture(DiffuseSampler, texCoord).rgb, 1);
    }
}
