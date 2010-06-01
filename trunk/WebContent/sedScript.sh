rm appo
rm appo1
rm $1.old
mv $1 $1.old
cat jspheader > appo1
cat $1.old >> appo1
sed 's/<?/<%/' appo1 > $1
