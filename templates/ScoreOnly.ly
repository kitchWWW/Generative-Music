\header{
	tagline = "" 
	title = "Vivaldi's Epoch"
	subtitle="For String Orchestra"
}

\paper{
  indent = 2\cm
  left-margin = 1.5\cm
  right-margin = 1.5\cm
  top-margin = 2\cm
  bottom-margin = 1.5\cm
  ragged-last-bottom = ##t
  print-all-headers = ##t
  print-page-number = ##f
}

\score{
\header{
	tagline = "" 
	title = "  "
	subtitle="  "
}
 \new  StaffGroup  <<
\new Staff \with {
    instrumentName = #"
%name0
"
	midiInstrument = "Violin"
  }
\absolute {

%part0
	
	\bar "|."
}
\new Staff \with {
    instrumentName = #"
%name1
"
	midiInstrument = "Violin"
  }
\absolute {
%part1

}

\new Staff \with {
    instrumentName = #"
%name2
"
	midiInstrument = "Viola"
  }
\absolute {
	\clef alto
%part2

}

\new Staff \with {
    instrumentName = #"
%name3
"
	midiInstrument = "Cello"
  }
\absolute {
	\clef bass
%part3

}
\new Staff \with {
    instrumentName = #"
%name4
"
	midiInstrument = "Cello"
  }
\absolute {
	\clef bass
%part4

}
\new Staff \with {
    instrumentName = #"
%name5
"
	midiInstrument = "Cello"
  }
\absolute {
	\clef bass
%part5

}

>>
\midi{}
\layout{}
}
