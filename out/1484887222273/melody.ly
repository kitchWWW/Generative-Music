#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c'8 b8 e''''''16 f''''''16 e''''''8 f''''''8 g''''''16 f''''''16 e''''''4 d''''''4 c''''''8 b'''''16 e'''''16 f'''''8. g'''''16 a'''''8. f'''''16 d'''''8 c'''''16 e''''16 d''''8 e''''8 f''''8 a''''16 g'''''16 a'''''4 b'''''8 a'''''8 g'''''8 a'''''8 b'''''4 g16 f8. e4 \bar"|."
}

>>
\midi{}
\layout{}
}
