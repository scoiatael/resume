%% Based on https://ctan.org/pkg/res?lang=en
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{resume}[2019/11/16 My custom CV class]
\PassOptionsToClass{11pt,12pt}{article}
\LoadClassWithOptions{article}

\nofiles

\RequirePackage{titlesec}
\titleformat{\section}         % Customise the \section command
            {\Large\scshape\raggedright} % Make the \section headers large (\Large),
            % small capitals (\scshape) and left aligned (\raggedright)
            {}{0em}                      % Can be used to give a prefix to all sections, like 'Section ...'
            {\llap}                           % Can be used to insert code before the heading
            []

\titlespacing{\section}{0em}{2em}{0em}

\titleformat{\subsection}
            {\large\scshape\raggedright}
            {}{0em}
            {}

\titlespacing{\subsection}{0em}{0.5em}{0em}

\titleformat{\subsubsection}
            {\bfseries\scshape\raggedright}
            {}{0em}
            {}

\newcommand{\name}[2]{%
  \centerline{\Huge{#1}}
  \centerline{\Large{#2}}
  \vspace{1cm}
}

\newcommand{\datedsubsection}[2]{%
  \subsection[#1]{#1 \hfill {\small #2}}%
}
