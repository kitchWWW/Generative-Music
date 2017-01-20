#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c'8 d'8 e'8 d'8 c'8 d'8 e'8 f'8 g'16 a'16 g'16 b16 a4 c'8 b8 a4 b8 c'8 d'8 e'8 f'4 d'16 e'8 f'16 g'4 d''16 c''8. b'8 a'8 g'8. b16 c'4 \bar"|."
}

>>
\midi{}
\layout{}
}
