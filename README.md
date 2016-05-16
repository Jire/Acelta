[![Build Status](https://travis-ci.org/Jire/Acelta.svg?branch=master)](https://travis-ci.org/Jire/Acelta)
[![Kotlin](https://img.shields.io/badge/kotlin-1.0.2-blue.svg)](http://kotlinlang.org)
[![license](https://img.shields.io/badge/license-GPL%203.0-yellowgreen.svg)](https://github.com/Jire/Acelta/blob/master/LICENSE)

# Acelta
RuneScape emulation rethought; Acelta reimagines with elegant, highly-performant solutions.

---

#### Zero-garbage packet receiving
Acelta receives packets in a straightforward, no-overhead dispatch system for which it is trivial to both listen and
define incoming packets. A zero-garbage, lock-free inter-thread messaging queue is used to link Netty's networking
threads with the game thread.

#### Zero-garbage packet sending
Acelta uses simplistic extension-function definitions to define outgoing packets. This system works in concert with
the "one buffer per session" approach to minimize write overhead.

#### Single game thread
Using a single game thread allows you to use simplistic, overhead-free techniques and libraries for game logic
if you so desire.

---

### Credits

**Sino**, **thing1**, **Colby**, **Jonatino**, **Wizard Jesse**, **Flammable**, and **Velocity** - for chat, banter,
and remarks about the way of design, and consultancy during the development process.

**Graham Edgecombe** - for his previous work on frameworks like Hyperion, Apollo, and ScapeEmulator which significantly
impacted and redefined the ways RuneScape emulators are written.