#version 150

#define _PI_ 3.14159265359

uniform sampler2D DiffuseSampler;
uniform vec3 Level;
uniform vec2 Center;
uniform vec3 ColorModulate;

in vec2 texCoord;
out vec4 fragColor;

void main(){
    vec2 patchedCoord = texCoord - Center;

    float r = texture(DiffuseSampler, patchedCoord / Level.r + Center).r;
    float g = texture(DiffuseSampler, patchedCoord / Level.g + Center).g;
    float b = texture(DiffuseSampler, patchedCoord / Level.b + Center).b;

    if(ColorModulate.r <= 0.0001){
        fragColor = vec4(
            vec3((r+g+b) / 3),
            1
        );
    }
    else{
        fragColor = vec4(
            vec3(r,g,b) * ColorModulate,
            1
        );
    }
}
