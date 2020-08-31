$(function(){
	var topBtn=$('#pageTop');
	 
	// ◇ボタンをクリックしたら、スクロールして上に戻る
	topBtn.click(function(){
	  $('body,html').animate({
	  scrollTop: 0},500);
	  return false;
	});

});