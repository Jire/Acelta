[![Build Status](https://travis-ci.org/Jire/Acelta.svg?branch=master)](https://travis-ci.org/Jire/Acelta)
[![Kotlin](https://img.shields.io/badge/kotlin-1.0.2-blue.svg)](http://kotlinlang.org)
[![license](https://img.shields.io/badge/license-GPL%203.0-yellowgreen.svg)](https://github.com/Jire/Acelta/blob/master/LICENSE)

# Acelta
RuneScape emulation rethought; Acelta reimagines with elegant, highly-performant solutions.

---

#### Zero-garbage packet receiving
Acelta receives packets in a straightforward, no-overhead dispatch system for which it is trivial to both listen and
define incoming packets.

`PacketConductor` eliminates the necessity for individual pipeline handlers for different game states.

#### Zero-garbage packet sending
Acelta uses simplistic function definitions to define outgoing packets. This system works in concert with
the "one buffer per session" approach to minimize write overhead.

Because of this approach, no Netty encoder is needed. Packet queueing is completely avoided for both incoming and
outgoing packets in favor of spare flushing.

#### Single thread for the whole server
Using a single game thread allows you to use simplistic, overhead-free techniques and libraries.

---

### Credits

**Sino**, [**thing1**](https://github.com/RyanDennis03), [**Dane**](https://github.com/thedaneeffect/), **Colby**,
[**Jonatino**](https://github.com/Jonatino), **Wizard Jesse**, **Flammable**,
and [**Velocity**](https://github.com/Velocity-) - for chat, banter, and remarks about the way of design,
and consultancy during the development process.

[**Graham Edgecombe**](https://github.com/grahamedgecombe/) - for his previous work on frameworks like Hyperion, Apollo,
 and ScapeEmulator which significantly impacted and redefined the ways RuneScape emulators are written.

---

### Media

![](http://i.imgur.com/ucWA5UC.png)

---

### Sponsors

[![YourKit](https://www.yourkit.com/images/yklogo.png)](https://www.yourkit.com/java/profiler/index.jsp)

We use YourKit Java Profiler to ensure we're getting the performance we expect.

[![Netty](http://i.imgur.com/73YTNlm.png)](http://netty.io/)

Netty provides us with the high-performance networking and concurrent libraries used to achieve our performance.
We worked with the Netty team to optimize and improve our throughput.