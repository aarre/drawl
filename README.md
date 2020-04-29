Drawl is the world's best drawing language.

[![Build Status](https://travis-ci.com/aarre/drawl.svg?branch=master)](https://travis-ci.com/aarre/drawl)

<a href="https://scan.coverity.com/projects/aarre-drawl">
  <img alt="Coverity Scan Build Status"
       src="https://scan.coverity.com/projects/20902/badge.svg"/>
</a>

Drawl is an object-oriented domain-specific language for creating
vector graphics using Java. In comparison to alternatives like TikZ/PGF,
Asymptote, and Processing/Processing.js, Drawl has one clear advantage:
all dimensions are relative. This means that you don't need to
explicitly place objects at particular coordinates, something you
might not know in advance without a lot of planning. Instead, you
place objects relative to each other. This is a more intuitive way
to draw. The only other drawing language that has this feature is
Diagrams, which is written in Haskell.