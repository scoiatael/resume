FROM ubuntu:bionic

ENV CLOJURE_DEPS "leiningen"
ENV TEX_DEPS "texlive-latex-base texlive-latex-extra texlive-fonts-recommended texlive-fonts-extra xzdec"

RUN ln -snf /usr/share/zoneinfo/Etc/UTC /etc/localtime \
  && echo "Etc/UTC" > /etc/timezone \
  && apt-get update \
  && apt-get upgrade -y \
  && apt-get install $CLOJURE_DEPS $TEX_DEPS locales -y \
  && rm -rf /var/lib/apt/lists/*

RUN sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen && locale-gen
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

WORKDIR /usr/local/resume
