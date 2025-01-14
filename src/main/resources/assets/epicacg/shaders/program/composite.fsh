#version 150

uniform sampler2D DiffuseSampler; //particle
uniform sampler2D DiffuseSampler2; //original

in vec2 texCoord;
out vec4 fragColor;

void main(){
    vec4 orig = texture(DiffuseSampler2, texCoord);
    vec4 part = texture(DiffuseSampler, texCoord);

    float alp =  part.a * part.a;
    fragColor = vec4(
        part.rgb * part.a + orig.rgb * (1 - alp)
    , 1);
    //fragColor = vec4(cut.x > 0.1 ? vec3(1,1,1) : col.rgb,1);
}
