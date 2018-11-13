/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.djrapitops.plugin.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TreeCmdNodeTest {

    private static final String METHOD_ONE_CALL = "Called Test SubCommand 'one' with args: ";
    private static final String METHOD_TWO_CALL = "Called Test SubCommand 'two with args: ";
    private static final String TWO_TEST_PERMISSION = "test.permission";
    private TreeCmdNode underTest;

    private Sender sender;

    @Before
    public void setUp() {
        sender = Mockito.mock(Sender.class);
        SenderType player = SenderType.PLAYER;
        when(sender.getSenderType()).thenReturn(player);

        underTest = new TreeCmdNode("test|two", "", CommandType.ALL, null);
        underTest.setInDepthHelp("Wrong in depth help");
        underTest.setDefaultCommand("one");
        populateSubCommands();
    }

    private void populateSubCommands() {
        CommandNode one = new CommandNode("one|alias", "", CommandType.ALL) {
            @Override
            public void onCommand(Sender sender, String commandLabel, String[] args) {
                sender.sendMessage(METHOD_ONE_CALL + Arrays.toString(args));
            }
        };
        one.setInDepthHelp("In Depth", "Once again");

        CommandNode two = new CommandNode("two|alias2", TWO_TEST_PERMISSION, CommandType.ALL) {
            @Override
            public void onCommand(Sender sender, String commandLabel, String[] args) {
                sender.sendMessage(METHOD_TWO_CALL + Arrays.toString(args));
            }
        };
        underTest.setNodeGroups(new CommandNode[]{
                one, two
        });
    }

    @Test
    public void argumentsAreParsedCorrectlyDefaultCommandNoArgs() {
        underTest.onCommand(sender, "", new String[]{"one"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_ONE_CALL + "[]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void argumentsAreParsedCorrectlyDefaultCommand() {
        underTest.onCommand(sender, "", new String[]{"one", "argument"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_ONE_CALL + "[argument]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void argumentsAreParsedCorrectlyNonDefaultCommand() {
        when(sender.hasPermission(TWO_TEST_PERMISSION)).thenReturn(true);

        underTest.onCommand(sender, "", new String[]{"two"});

        verify(sender).getSenderType();
        verify(sender).hasPermission(TWO_TEST_PERMISSION);
        verify(sender).sendMessage(METHOD_TWO_CALL + "[]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void aliasExecutesCommand() {
        underTest.onCommand(sender, "", new String[]{"alias", "test"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_ONE_CALL + "[test]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void wrongCommandExecutesDefaultCommandWithArgument() {
        underTest.onCommand(sender, "", new String[]{"non-existing-command"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_ONE_CALL + "[non-existing-command]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void noArgsDisplaysHelp() {
        underTest.onCommand(sender, "", new String[]{});

        verify(sender).sendMessage("§f>");
    }

    @Test
    public void questionArgDisplaysInDepthHelp() {
        underTest.onCommand(sender, "", new String[]{"?"});

        verify(sender).sendMessage("Aliases: [test, two]");
    }

    @Test
    public void questionArgDisplaysSubCommandInDepthHelp() {
        underTest.onCommand(sender, "", new String[]{"one", "?"});

        verify(sender).sendMessage("Aliases: [one, alias]");
    }

    @Test
    public void onlyPermissionGrantedHelpIsShown() {
        when(sender.hasPermission(TWO_TEST_PERMISSION)).thenReturn(false);
        List<String> expected = Arrays.asList(
                "§f> SubCommands §f/test",
                "  ",
                "§f  one §f",
                "  ",
                "  §fAdd ? to the end of the command for more help",
                "§f>"
        );
        List<String> result = underTest.getHelpCommand().getHelpMessages(sender);
        verify(sender).hasPermission(TWO_TEST_PERMISSION);
        verifyNoMoreInteractions(sender);
        assertEquals(expected, result);
    }

    @Test
    public void permissionCheckedBeforeExecutingCommand() {
        when(sender.hasPermission(TWO_TEST_PERMISSION)).thenReturn(false);

        underTest.onCommand(sender, "", new String[]{"two"});

        verify(sender).getSenderType();
        verify(sender).hasPermission(TWO_TEST_PERMISSION);
        verify(sender).sendMessage("§cYou do not have the required permission.");
        verifyNoMoreInteractions(sender);
    }
}