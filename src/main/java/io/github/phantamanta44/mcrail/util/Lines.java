package io.github.phantamanta44.mcrail.util;

import org.bukkit.block.Sign;

public class Lines {
    
    private final Sign sign;
    
    public Lines(Sign sign) {
        this.sign = sign;
    }

    public String header() {
        return sign.getLine(0);
    }
    
    public String a() {
        return sign.getLine(1);
    }

    public String b() {
        return sign.getLine(2);
    }

    public String c() {
        return sign.getLine(3);
    }

    public void a(String line) {
        sign.setLine(1, line);
        sign.update();
    }

    public void b(String line) {
        sign.setLine(2, line);
        sign.update();
    }

    public void c(String line) {
        sign.setLine(3, line);
        sign.update();
    }
    
}
