#version 150

//copied https://github.com/Low-Drag-MC/Shimmer

uniform sampler2D DiffuseSampler;
uniform sampler2D DownTexture;
uniform sampler2D Background;
uniform vec2 OutSize;
uniform float BloomIntensive;
uniform float BloomBase;
uniform float BloomThresholdUp;
uniform float BloomThresholdDown;

in vec2 texCoord;
out vec4 fragColor;

void main(){
    vec3 textel = vec3(1., -1., 0.) / OutSize.xyx;

    vec4 out_colour = texture(DiffuseSampler, texCoord + textel.xx); // 1 1
    out_colour += texture(DiffuseSampler, texCoord + textel.xz) * 2.0; // 1 0
    out_colour += texture(DiffuseSampler, texCoord + textel.xy); // 1 -1
    out_colour += texture(DiffuseSampler, texCoord + textel.yz) * 2.0; // -1 0
    out_colour += texture(DiffuseSampler, texCoord) * 4.0; // 0 0
    out_colour += texture(DiffuseSampler, texCoord + textel.zx) * 2.0; // 0 1
    out_colour += texture(DiffuseSampler, texCoord + textel.yy); // -1 -1
    out_colour += texture(DiffuseSampler, texCoord + textel.zy) * 2.0; // 0 -1
    out_colour += texture(DiffuseSampler, texCoord + textel.yx); // -1 1

    vec4 highLight = texture(DownTexture, texCoord);
    vec4 bloom = BloomIntensive * vec4(out_colour.rgb * 0.8 / 16. + highLight.rgb * 0.8, 1.);

    vec4 background = texture(Background, texCoord);
    background.rgb = background.rgb * (1 - highLight.a) + highLight.a * highLight.rgb;
    float max = max(background.b, max(background.r, background.g));
    float min = min(background.b, min(background.r, background.g));
    fragColor = vec4(background.rgb + bloom.rgb * ((1. - (max + min) / 2.) * (BloomThresholdUp - BloomThresholdDown) + BloomThresholdDown + BloomBase), 1.);
}