package com.dfdyz.epicacg.client.feedback.channel;

import com.dfdyz.epicacg.client.feedback.clip.AbstractRumbleClip;

public abstract class AbstractRumbleChannel {
        protected int age = 0;
        protected final int lifetime;
        private boolean removed = false;

        public AbstractRumbleChannel(int lifetime) {
            this.lifetime = lifetime;
        }

        public void tick() {
            if (age++ >= lifetime) {
                remove();
            }
        }

        public abstract float samplerH() ;

        public abstract float samplerL() ;

        public void remove() {
            removed = true;
        }

        public boolean isRemoved() {
            return removed;
        }
}