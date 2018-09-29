package mock;

import com.djrapitops.plugin.command.Sender;
import com.djrapitops.plugin.command.SenderType;

public class MockCMDSender implements Sender {

    private String lastMsg;

    public String getLastMsg() {
        return lastMsg;
    }

    @Override
    public void sendMessage(String string) {
        lastMsg = string;
    }

    @Override
    public void sendMessage(String[] strings) {

    }

    @Override
    public void sendLink(String pretext, String message, String url) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasPermission(String string) {
        return true;
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.CONSOLE;
    }

    @Override
    public Object getSender() {
        return null;
    }
}