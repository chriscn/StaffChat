package github.chriscn.channel;

import github.chriscn.StaffChat;

public class ExampleChannel extends VirtualChannel{

    StaffChat plugin;
    public ExampleChannel(StaffChat instance) {
        super("example", "example.read", "example.write", "examplechat");
    }
}
