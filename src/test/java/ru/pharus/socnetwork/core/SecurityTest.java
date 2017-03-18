package ru.pharus.socnetwork.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityTest {
    @Test
    void checkHash() {
        assertTrue(Security.checkHash("test","123","7288edd0fc3ffcbe93a0cf06e3568e28521687bc"));
        assertFalse(Security.checkHash("bla@bla","bum", "7288edd0fc3ffcbe93a0cf06e3568e28521687bc"));
    }

    @Test
    void generateHash() {
        assertEquals(Security.generateHash("test","123"), "7288edd0fc3ffcbe93a0cf06e3568e28521687bc");
    }

}