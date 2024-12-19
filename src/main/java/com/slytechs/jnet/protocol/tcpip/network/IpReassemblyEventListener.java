package com.slytechs.jnet.protocol.tcpip.network;

/**
 * Listener interface for IP fragment reassembly events.
 */
@FunctionalInterface
public interface IpReassemblyEventListener {
    
    /**
     * Called when a reassembly event occurs.
     *
     * @param event the reassembly event details
     */
    void onEvent(IpReassemblyEvent event);
}