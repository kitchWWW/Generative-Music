#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c'16 b16 c'8 e'16 f'16 g'16 b'16 c''8 d''8 e''8 d''8 c''4 a'8 g'8 f'8 e'16 d'16 c'4 d'4 e'4 d'8 e'8 f'8 e'8 d'16 e'16 d'16 c'16 b8 a8 g8 a8 b4 c'4 \bar"|."
}

>>
\midi{}
\layout{}
}
