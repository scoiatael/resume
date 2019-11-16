# resume

A Clojure site for buidling my resume

## Dependencies

Requires `pdflatex` on `PATH`. TODO: Describe getting it on macOS :)

## Usage

`org/experience.org` serves as data file, to be edited with Emacs org-mode (or any other editor with org-mode support).

Lein tasks can be used to convert into:
- [`resume.json`](https://jsonresume.org) - and then HTML via `lein export`,
- Latex source for PDF [TODO]


## License

Copyright © 2019 Łukasz Czapliński

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
