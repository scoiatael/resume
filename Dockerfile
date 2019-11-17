FROM ubuntu:bionic

ENV CLOJURE_DEPS "leiningen"
ENV TEX_DEPS "texlive-latex-base texlive-latex-extra texlive-fonts-recommended texlive-fonts-extra xzdec"

RUN ln -snf /usr/share/zoneinfo/Etc/UTC /etc/localtime \
  && echo "Etc/UTC" > /etc/timezone \
  && apt-get update \
  && apt-get upgrade -y \
  && apt-get install $CLOJURE_DEPS $TEX_DEPS -y \
  && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/local/resume
