# Introduction to resume

## Why?

I used to write CV in TeX. This proved to be quite cumbersome to update, as it mixes presentation with data. I wanted something easier - like [org files in Emacs](https://orgmode.org). And then export it to look nicely.

My first attempt uses [JSON](jsonresume.org).

## Doing your own

Open `org/experience.org` in your favourite editor, which hopefully supports Org files. Edit, and then run `lein export`. This should generate files to `target/dist` - HTML and JSON version. PDF coming soon!
