package com.djrapitops.plugin.command;

import mock.MockCMDSender;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TreeCmdNodeTest {

    private List<Boolean> calls = new ArrayList<>();
    private TreeCmdNode treeCmdNode;
    private MockCMDSender mockCMDSender;

    @Before
    public void setUp() {
        calls.clear();

        mockCMDSender = new MockCMDSender();
        treeCmdNode = new TreeCmdNode("test|two", "", CommandType.ALL, null);
        treeCmdNode.setInDepthHelp("Wrong in depth help");
        treeCmdNode.setDefaultCommand("one");

        CommandNode test = new CommandNode("one|alias", "", CommandType.ALL) {
            @Override
            public void onCommand(Sender sender, String commandLabel, String[] args) {
                calls.add(true);
            }
        };
        test.setInDepthHelp("In Depth", "Once again");
        treeCmdNode.setNodeGroups(new CommandNode[]{
                test
        });
    }

    @Test
    public void onCommand() {
        treeCmdNode.onCommand(mockCMDSender, "", new String[]{"one"});
        assertEquals(1, calls.size());
    }

    @Test
    public void onCommandHelpEmpty() {
        treeCmdNode.onCommand(mockCMDSender, "", new String[0]);
        assertEquals(0, calls.size());
        assertEquals("§f>", mockCMDSender.getLastMsg());
    }

    @Test
    public void onCommandWrongCMD() {
        treeCmdNode.onCommand(mockCMDSender, "", new String[]{""});
        assertEquals(1, calls.size());
        assertNull(mockCMDSender.getLastMsg());
    }

    @Test
    public void onCommandInDepthHelp() {
        treeCmdNode.onCommand(mockCMDSender, "", new String[]{"?"});
        assertEquals(0, calls.size());
        assertEquals("Aliases: [test, two]", mockCMDSender.getLastMsg());
    }

    @Test
    public void onCommandInDepthHelpSubCmd() {
        treeCmdNode.onCommand(mockCMDSender, "", new String[]{"one", "?"});
        assertEquals(0, calls.size());
        assertEquals("Aliases: [one, alias]", mockCMDSender.getLastMsg());
    }
}