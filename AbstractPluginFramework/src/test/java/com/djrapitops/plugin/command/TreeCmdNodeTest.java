package com.djrapitops.plugin.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class TreeCmdNodeTest {

    private static final String METHOD_ONE_CALL = "Called Test SubCommand 'one' with args: ";
    private static final String METHOD_TWO_CALL = "Called Test SubCommand 'two with args: ";
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

        CommandNode one = new CommandNode("one|alias", "", CommandType.ALL) {
            @Override
            public void onCommand(Sender sender, String commandLabel, String[] args) {
                sender.sendMessage(METHOD_ONE_CALL + Arrays.toString(args));
            }
        };
        one.setInDepthHelp("In Depth", "Once again");

        CommandNode two = new CommandNode("two|alias2", "", CommandType.ALL) {
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
    public void onCommandOne() {
        underTest.onCommand(sender, "", new String[]{"one"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_ONE_CALL + "[]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void onCommandOneArgument() {
        underTest.onCommand(sender, "", new String[]{"one", "argument"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_ONE_CALL + "[argument]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void onCommandTwo() {
        underTest.onCommand(sender, "", new String[]{"two"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_TWO_CALL + "[]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void onCommandAlias() {
        underTest.onCommand(sender, "", new String[]{"alias", "test"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_ONE_CALL + "[test]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void onCommandWrongCMD() {
        underTest.onCommand(sender, "", new String[]{"non-existing-command"});

        verify(sender).getSenderType();
        verify(sender).sendMessage(METHOD_ONE_CALL + "[non-existing-command]");
        verifyNoMoreInteractions(sender);
    }

    @Test
    public void onCommandHelpEmpty() {
        underTest.onCommand(sender, "", new String[]{});

        verify(sender).sendMessage("§f>");
    }

    @Test
    public void onCommandInDepthHelp() {
        underTest.onCommand(sender, "", new String[]{"?"});

        verify(sender).sendMessage("Aliases: [test, two]");
    }

    @Test
    public void onCommandInDepthHelpSubCmd() {
        underTest.onCommand(sender, "", new String[]{"one", "?"});

        verify(sender).sendMessage("Aliases: [one, alias]");
    }
}